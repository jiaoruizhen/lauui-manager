package com.dognessnetwork.ops.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dognessnetwork.ops.domain.Dictionary;
import com.dognessnetwork.ops.dto.DictionaryVo;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.repository.DictionaryRepository;
import com.dognessnetwork.ops.service.DictionaryService;
import com.dognessnetwork.ops.utils.ReadExcelUtils;
import com.dognessnetwork.ops.utils.UpdateUtil;

@Transactional
@Service
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	private DictionaryRepository dictionaryRepository;

	@Override
	public Dictionary save(Dictionary vo) {
		Dictionary d = null;
		if (vo.getId() == null) {
			d = new Dictionary();
		} else {
			d = dictionaryRepository.findById(vo.getId()).get();
		}
		UpdateUtil.copyNonNullProperties(vo, d);
		dictionaryRepository.save(d);
		return d;
	}

	@Override
	public Object all(Dictionary dic) {
		List<Dictionary> dicList = null;
		String code = dic.getCode();
		Long id = dic.getId();
		String name = dic.getName();

		if (!StringUtils.isEmpty(code) || !StringUtils.isEmpty(name)) {
			ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",
					ExampleMatcher.GenericPropertyMatchers.startsWith());
			dicList = dictionaryRepository.findAll(Example.of(dic, matcher));
			return getVoList(dicList);
		} else {
			return dictionaryRepository.findByPId(id == null ? (long) 0 : id);
		}
	}

	private Object getVoList(List<Dictionary> dicList) {
		DictionaryVo vo = null;
		List<DictionaryVo> vos = new ArrayList<>();
		for (Dictionary dictionary : dicList) {
			vo = new DictionaryVo();
			vo.setDictionary(dictionary);
			vos.add(getPidObj(dictionary.getpId(), vo));
		}
		return vos;
	}

	private DictionaryVo getPidObj(Long id, DictionaryVo ch) {
		Optional<Dictionary> pDic = dictionaryRepository.findById(id);
		DictionaryVo vo = null;
		if (pDic.isPresent()) {
			Dictionary dictionary = pDic.get();
			vo = new DictionaryVo();
			vo.setDictionary(dictionary);
			vo.setChildren(ch);
			return getPidObj(dictionary.getpId(), vo);
		} else {
			return ch;
		}
	}

	@Override
	public long count() {
		return dictionaryRepository.count();
	}

	@Override
	public Dictionary findById(Long id) {
		return dictionaryRepository.findById(id).get();
	}

	private List<Dictionary> dellist(Long id) {
		List<Dictionary> list = dictionaryRepository.findByPId(id);
		for (Dictionary category : list) {
			Long id2 = category.getId();
			List<Dictionary> cs = dellist(id2);
			if (!cs.isEmpty()) {
				dictionaryRepository.deleteInBatch(cs);
			}
		}
		dictionaryRepository.deleteById(id);
		return list;
	}

	@Override
	public void del(Long id) {
		dellist(id);
	}

	@Override
	public void delBatch(String id) {
		String[] split = id.split(",");
		Long[] str2 = new Long[split.length];
		for (int i = 0; i < split.length; i++) {
			str2[i] = Long.valueOf(split[i]);
		}
		List<Long> asList = Arrays.asList(str2);
		dictionaryRepository.deleteByIds(asList);
	}

	@Override
	public Response ImportDictiontry(MultipartFile file) {
		List<Dictionary> dics = new ArrayList<>();
		try {
			ReadExcelUtils read = new ReadExcelUtils(file.getInputStream(), file.getOriginalFilename());
			List<List<Map<String, String>>> content = read.readExcelContent();
			for (List<Map<String, String>> item : content) {
				getImportDics(item, dics);
			}
			dictionaryRepository.saveAll(dics);
			return Response.SUCCESS();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ERROR(e.getMessage(), 3000);
		}
	}

	private void getImportDics(List<Map<String, String>> content, List<Dictionary> dics) throws Exception {
		Map<String, String> map0 = content.get(0);

		Long cnPid = getPid(map0.get("0"));
		Long dePid = getPid(map0.get("1"));
		Long enPid = getPid(map0.get("2"));
		Long frPid = getPid(map0.get("3"));
		Long jpPid = getPid(map0.get("4"));
		Long hkPid = getPid(map0.get("5"));

		String langType = map0.get("6");
		Dictionary dic = null;

		for (int i = 2; i < content.size(); i++) {
			Map<String, String> map = content.get(i);
			String code = map.get("6");
			dic = new Dictionary();
			dic.setType(langType);
			dic.setLangType("zh");
			dic.setCode(code);
			dic.setpId(cnPid);
			dic.setName(map.get("0"));
			dics.add(dic);

			dic = new Dictionary();
			dic.setType(langType);
			dic.setLangType("de");
			dic.setCode(code);
			dic.setpId(dePid);
			dic.setName(map.get("1"));
			dics.add(dic);

			dic = new Dictionary();
			dic.setType(langType);
			dic.setLangType("en");
			dic.setCode(code);
			dic.setpId(enPid);
			dic.setName(map.get("2"));
			dics.add(dic);

			dic = new Dictionary();
			dic.setType(langType);
			dic.setLangType("fr");
			dic.setCode(code);
			dic.setpId(frPid);
			dic.setName(map.get("3"));
			dics.add(dic);

			dic = new Dictionary();
			dic.setType(langType);
			dic.setLangType("jp");
			dic.setCode(code);
			dic.setpId(jpPid);
			dic.setName(map.get("4"));
			dics.add(dic);

			dic = new Dictionary();
			dic.setType(langType);
			dic.setLangType("hk");
			dic.setCode(code);
			dic.setpId(hkPid);
			dic.setName(map.get("5"));
			dics.add(dic);
		}
	}

	private Long getPid(String pid) throws Exception {
		pid = StringUtils.isEmpty(pid) ? "0" : pid.substring(0, pid.lastIndexOf("."));
		Long cnPid = Long.parseLong(pid);
		if (!"0".equals(pid)) {
			Optional<Dictionary> o = dictionaryRepository.findById(Long.parseLong(pid));
			if (!o.isPresent()) {
				throw new Exception("Excel表格中内容有错，没有这个父节点!");
			}
		}
		return cnPid;
	}

	@Override
	public Dictionary getByCode(String code, String langType) {
		return dictionaryRepository.findByCodeAndLangType(code, langType);
	}

	@Override
	public List<Dictionary> getByType(String type, String langType) {
		return dictionaryRepository.findByTypeAndLangType(type, langType);
	}
}
