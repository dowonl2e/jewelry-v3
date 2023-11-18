package com.jewelry.aop;

import com.jewelry.cms.menu.domain.MenuAuthVO;
import com.jewelry.cms.menu.dto.MenuAuthDto;
import com.jewelry.cms.menu.dto.MenuAuthResponseDto;
import com.jewelry.cms.menu.entity.MenuAuthRepositoryImpl;
import com.jewelry.cms.menu.mapper.MenuAuthMapper;
import com.jewelry.common.domain.CommonTO;
import com.jewelry.common.domain.CommonVO;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.common.dto.CommonDto;
import com.jewelry.exception.CustomException;
import com.jewelry.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthorityAspect {

  private final MenuAuthRepositoryImpl menuAuthRepository;

  @Pointcut("@annotation(com.jewelry.annotation.MenuAuthAnt)")
  private void authorityPointCut() {}

  /**
   * MenuAuthAnt 어노테이션이 선언된 Service의 메서드의 경우 권한 확인
   * @param pjp
   * @return
   * @throws Throwable
   */
  @Around("authorityPointCut()")
  public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
    log.info("[****************************** 메뉴 권한 조회 AOP(Before) ***********************************");

    MenuAuthResponseDto authResponseDto = null;
    Object[] paramObj = pjp.getArgs();
    for(Object obj : paramObj){
      //CommonTO 파라미터의 값으로 권한 조회
      if (obj instanceof CommonDto){
        MenuAuthDto menuAuthDto = new MenuAuthDto();
        menuAuthDto.setUserId(((CommonDto) obj).getUserId());
        menuAuthDto.setMenuId(((CommonDto) obj).getMenuId());
        authResponseDto = menuAuthRepository.getMenuAuth(menuAuthDto);
        break;
      }
      else if (obj instanceof SearchDto){
        MenuAuthDto menuAuthDto = new MenuAuthDto();
        menuAuthDto.setUserId(((SearchDto) obj).getUserId());
        menuAuthDto.setMenuId(((SearchDto) obj).getMenuId());
        authResponseDto = menuAuthRepository.getMenuAuth(menuAuthDto);
        break;
      }
    }

    //권한자체가 없을 경우 초기화
    if(authResponseDto == null){
      log.info("권한이 없습니다.");
      authResponseDto = new MenuAuthResponseDto("N","N","N","N","N");
    }

    String methodName = pjp.getSignature().getName().toLowerCase();
    boolean isIUD = false, isAuthed = false;
    if(methodName.indexOf("insert") == 0){
      isIUD = true;
      isAuthed = ObjectUtils.nullSafeEquals(authResponseDto.getWriteAuth(), "Y") ? true : false;
    }
    else if(methodName.indexOf("update") == 0){
      isIUD = true;
      if(methodName.indexOf("delete") > -1)
        isAuthed = ObjectUtils.nullSafeEquals(authResponseDto.getRemoveAuth(), "Y") ? true : false;
      else
        isAuthed = ObjectUtils.nullSafeEquals(authResponseDto.getModifyAuth(), "Y") ? true : false;
    }
    else if(methodName.indexOf("delete") == 0){
      isIUD = true;
      isAuthed = ObjectUtils.nullSafeEquals(authResponseDto.getRemoveAuth(), "Y") ? true : false;
    }

    if(isIUD && !isAuthed){
      throw new CustomException(ResponseCode.FORBIDDEN);
    }

    Object result = pjp.proceed();
    if(!isIUD) {
      log.info("[****************************** 메뉴 권한 조회 AOP(After) ***********************************");
      if (result instanceof CommonVO) {
        ((CommonVO) result).setMenuAuthResponseDto(authResponseDto);
      } else if (result instanceof Map<?, ?>) {
        ((Map<String, Object>) result).put("menuAuthResponseDto", authResponseDto);
      }
    }
    return result;
  }
}
