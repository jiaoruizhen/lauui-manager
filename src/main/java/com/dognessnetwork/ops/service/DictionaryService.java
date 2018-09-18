package com.dognessnetwork.ops.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dognessnetwork.ops.domain.Dictionary;
import com.dognessnetwork.ops.dto.Response;

public interface DictionaryService {

	Dictionary save(Dictionary vo);

	Object all(Dictionary dic);

	long count();

	Dictionary findById(Long id);

	void delBatch(String id);

	void del(Long id);

	Response ImportDictiontry(MultipartFile file);

	Dictionary getByCode(String code, String langType);

	List<Dictionary> getByType(String type, String langType);

}
