package by.pavelkht.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AopLogging {
    //TODO поинткаты

    // create user
    @Pointcut("execution(* by.pavelkht.*.service.UserService.createUser(..))")
    public void createUserPointcut() {
    }

    // update user
    @Pointcut("execution(* by.pavelkht.*.service.UserService.update(..))")
    public void updateUserPointcut() {
    }

    // delete user
    @Pointcut("execution(* by.pavelkht.*.service.UserService.deleteUser(..))")
    public void deleteUserPointcut() {
    }

    //TODO Адвайсы

    @Around("by.pavelkht.aop.AopLogging.createUserPointcut()"
            + "&& args(user)")
    public Object loggingCreate(ProceedingJoinPoint joinPoint, Object user) throws Throwable {
        Object service = joinPoint.getTarget();
        log.info("AROUND before create user method in class {} with data: {}", service, user);
        try{
            Object result = joinPoint.proceed();
            log.info("AROUND after returning crate user method in class {} with result: {}", service, result);
            return result;
        } catch (Throwable ex){
            log.info("AROUND after throwing exception crate user method in class {} with exception: {}", service, ex.getMessage());
            throw ex;
        }
    }

    @Around(value = "by.pavelkht.aop.AopLogging.updateUserPointcut() && args(id, user)",
            argNames = "joinPoint,id,user")
    public Object loggingUpdate(ProceedingJoinPoint joinPoint, Object id, Object user) throws Throwable {
        Object service = joinPoint.getTarget();
        log.info("AROUND before update user id {} method in class {} with data: {}", id, service, user);
        try{
            Object result = joinPoint.proceed();
            log.info("AROUND after returning update user method in class {} with result: {}", service, result);
            return result;
        } catch (Throwable ex){
            log.info("AROUND after throwing exception update user method in class {} with exception: {}", service, ex.getMessage());
            throw ex;
        }
    }

    @Around("by.pavelkht.aop.AopLogging.deleteUserPointcut()"
            + "&& args(id)")
    public Object loggingDelete(ProceedingJoinPoint joinPoint, Object id) throws Throwable {
        Object service = joinPoint.getTarget();
        log.info("AROUND before delete user with id {} method in class {}", id, service);
        try{
            Object result = joinPoint.proceed();
            log.info("AROUND after returning delete user method in class {} with result: {}", service, result);
            return result;
        } catch (Throwable ex){
            log.info("AROUND after throwing exception update user method in class {} with exception: {}", service, ex.getMessage());
            throw ex;
        }
    }
}
