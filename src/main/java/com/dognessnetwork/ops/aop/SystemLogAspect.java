package com.dognessnetwork.ops.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dognessnetwork.ops.config.security.SecurityUtils;
import com.dognessnetwork.ops.domain.SystemLogs;
import com.dognessnetwork.ops.dto.LoggerDto;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.dto.SystemLog;
import com.dognessnetwork.ops.dto.User;
import com.dognessnetwork.ops.service.SystemLogService;
import com.dognessnetwork.ops.utils.JsonUtil;


/**
 * @Description: 定义日志切入类
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:05
 * @Version: 1.0
 */
@Aspect
@Component
@Order(-5)
public class SystemLogAspect {

    @Autowired
    private SystemLogService systemLogService;
    
    
	private static final Logger log = LoggerFactory.getLogger(SystemLogAspect.class);

    /***
     * 定义service切入点拦截规则，拦截SystemServiceLog注解的方法
     */
    @Pointcut("@annotation(com.dognessnetwork.ops.aop.SystemServiceLog)")
    public void serviceAspect(){}

    /***
     * 定义controller切入点拦截规则，拦截SystemControllerLog注解的方法
     */
    @Pointcut("@annotation(com.dognessnetwork.ops.aop.SystemControllerLog)")
    public void controllerAspect(){}

    /***
     * 拦截控制层的操作日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public Response recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        SystemLog systemLog = new SystemLog();
        SystemLogs systemLogs=new SystemLogs();
        Object proceed = null ;
        //获取session中的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user=SecurityUtils.getUser();
        systemLogs.setUserId(user.getId());
        //获取请求的ip
        String ip = request.getRemoteAddr();
        systemLog.setRequestip(ip);
        //获取执行的方法名
        systemLog.setActionmethod(joinPoint.getSignature().getName());
        log.info("------------------------------------Request log---------------------------------------------");
		log.info("| url: " + request.getRequestURI());
		log.info("| ip: " + request.getRemoteHost());
		log.info("| user: "+user.getUsername());
        //获取方法执行前时间
        Date date=new Date();
        systemLog.setActiondate(date);
        proceed = joinPoint.proceed();
        //提取controller中ExecutionResult的属性
        Response result = (Response) proceed;
        	int status=result.getHeader().getStatus();
        if (status==1000){//成功
            //设置操作信息
            systemLog.setType("1");
            //获取执行方法的注解内容
            LoggerDto loggerDto=getControllerMethodDescription(joinPoint);
            systemLog.setTableName(loggerDto.getTableName());
            systemLog.setActionType(loggerDto.getActionType());
            systemLog.setDescription(loggerDto.getDescription()+":"+result.getHeader().getMessage());
        }else{
            systemLog.setType("2");
            systemLog.setExceptioncode(result.getHeader().getMessage());
        }

        Object[] params = joinPoint.getArgs() ;
        String returnStr = "" ;
        for (Object param : params) {
            if (param instanceof String){
                returnStr+= param ;
            }else if (param instanceof Integer){
                returnStr+= param ;
            }
        }
        log.info("| Params: " +returnStr);
        log.info("| ResponseHeader: "+result.getHeader().toString());
        if(!StringUtils.isEmpty(result.getData())){
        	 log.info("| ResponseData: "+result.getData().toString());
        }else{
        	log.info("| ResponseData: null ");
        }
        systemLog.setParams(returnStr);
        systemLogs.setDescription(JsonUtil.objectToJson(systemLog));
        systemLogService.saveUser(systemLogs);

        return result ;
    }

    @Around("serviceAspect()")
    public Response recordLogs(ProceedingJoinPoint joinPoint) throws Throwable {
        SystemLog systemLog = new SystemLog();
        SystemLogs systemLogs=new SystemLogs();
        Object proceed = null ;
        //获取session中的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取请求的ip
        String ip = request.getRemoteAddr();
        systemLog.setRequestip(ip);
        //获取执行的方法名
        systemLog.setActionmethod(joinPoint.getSignature().getName());
        log.info("------------------------------------Request log---------------------------------------------");
		log.info("| url: " + request.getRequestURI());
		log.info("| ip: " + request.getRemoteHost());
		
        //获取方法执行前时间
        Date date=new Date();
        systemLog.setActiondate(date);

        proceed = joinPoint.proceed();
        //提取controller中ExecutionResult的属性
        Response result = (Response) proceed;
        	int status=result.getHeader().getStatus();
        if (status==1000){//成功
            //设置操作信息
            systemLog.setType("1");
            //获取执行方法的注解内容
            LoggerDto loggerDto=getServiceMethodMsg(joinPoint);
            systemLog.setTableName(loggerDto.getTableName());
            systemLog.setActionType(loggerDto.getActionType());
            systemLog.setDescription(loggerDto.getDescription()+":"+result.getHeader().getMessage());
        }else{
            systemLog.setType("2");
            systemLog.setExceptioncode(result.getHeader().getMessage());
        }

        Object[] params = joinPoint.getArgs() ;
        String returnStr = "" ;
        for (Object param : params) {
            if (param instanceof String){
                returnStr+= param ;
            }else if (param instanceof Integer){
                returnStr+= param ;
            }
        }
        log.info("| Params: " +returnStr);
        log.info("| ResponseHeader: "+result.getHeader().toString());
        log.info("| ResponseData: "+result.getData().toString());
        systemLog.setParams(returnStr);
        systemLogs.setDescription(JsonUtil.objectToJson(systemLog));
        systemLogService.saveUser(systemLogs);

        return result ;
    }
    
    @AfterThrowing(pointcut = "serviceAspect()",throwing="e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e) throws Throwable{
        SystemLog systemLog = new SystemLog();
        SystemLogs systemLogs=new SystemLogs();
        //获取session中的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取请求的ip
        String ip = request.getRemoteAddr();
        systemLog.setRequestip(ip);
        systemLog.setType("2");
        systemLog.setExceptioncode(e.getClass().getName());
        systemLog.setExceptiondetail(e.getMessage());
        systemLogs.setDescription(JsonUtil.objectToJson(systemLog));
        systemLogService.saveUser(systemLogs);
    }

    
    @AfterThrowing(pointcut = "controllerAspect()",throwing="e")
    public void doAfterThrowings(JoinPoint joinPoint,Throwable e) throws Throwable{
        SystemLog systemLog = new SystemLog();
        SystemLogs systemLogs=new SystemLogs();
        //获取session中的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        	User user=SecurityUtils.getUser();
        //获取请求的ip
        	systemLogs.setUserId(user.getId());
        String ip = request.getRemoteAddr();
        systemLog.setRequestip(ip);
        systemLog.setType("2");
        systemLog.setExceptioncode(e.getClass().getName());
        systemLog.setExceptiondetail(e.getMessage());
        systemLogs.setDescription(JsonUtil.objectToJson(systemLog));
        systemLogService.saveUser(systemLogs);
    }
    /***
     * 获取service的操作信息
     * @param joinpoint
     * @return
     * @throws Exception
     */
    public LoggerDto getServiceMethodMsg(JoinPoint joinpoint) throws Exception{
        //获取连接点目标类名
        String className =joinpoint.getTarget().getClass().getName() ;
        //获取连接点签名的方法名
        String methodName = joinpoint.getSignature().getName() ;
        //获取连接点参数
        Object[] args = joinpoint.getArgs() ;
        //根据连接点类的名字获取指定类
        @SuppressWarnings("rawtypes")
		Class targetClass = Class.forName(className);
        //拿到类里面的方法
        Method[] methods = targetClass.getMethods() ;

        LoggerDto loggerDto =new LoggerDto();
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                @SuppressWarnings("rawtypes")
				Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length){
                	loggerDto.setDescription(method.getAnnotation(SystemControllerLog.class).descrption());
                	loggerDto.setTableName(method.getAnnotation(SystemControllerLog.class).tableName());
                    loggerDto.setActionType(method.getAnnotation(SystemControllerLog.class).actionType());
                	break;
                }
            }
        }
        return loggerDto ;
    }

    /***
     * 获取controller的操作信息
     * @param point
     * @return
     */
    public LoggerDto getControllerMethodDescription(ProceedingJoinPoint point) throws  Exception{
        //获取连接点目标类名
        String targetName = point.getTarget().getClass().getName() ;
        //获取连接点签名的方法名
        String methodName = point.getSignature().getName() ;
        //获取连接点参数
        Object[] args = point.getArgs() ;
        //根据连接点类的名字获取指定类
        @SuppressWarnings("rawtypes")
		Class targetClass = Class.forName(targetName);
        //获取类里面的方法
        Method[] methods = targetClass.getMethods() ;
        LoggerDto loggerDto =new LoggerDto();
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                @SuppressWarnings("rawtypes")
				Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length){
                	loggerDto.setDescription(method.getAnnotation(SystemControllerLog.class).descrption());
                	loggerDto.setTableName(method.getAnnotation(SystemControllerLog.class).tableName());
                	if("1".equals(method.getAnnotation(SystemControllerLog.class).actionType())){//添加
                		loggerDto.setActionType("添加");
                	}
                	if("2".equals(method.getAnnotation(SystemControllerLog.class).actionType())){//添加
                		loggerDto.setActionType("删除");
                	}
                	if("3".equals(method.getAnnotation(SystemControllerLog.class).actionType())){//添加
                		loggerDto.setActionType("修改");
                	}
                	if("4".equals(method.getAnnotation(SystemControllerLog.class).actionType())){//添加
                		loggerDto.setActionType("查询");
                	}
                    break;
                }
            }
        }
        
        return loggerDto ;
    }

}
