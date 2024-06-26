package com.example.security.interceptor.post;

import com.example.security.domain.post.Post;
import com.example.security.domain.post.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Slf4j
public class PostAuthorizationInterceptor implements HandlerInterceptor {

  private final PostRepository postRepository;

  public PostAuthorizationInterceptor(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  // annotation 을 넣지 않아도, interceptor 이 적용될 경로를 미리 설정한다면 상관없다.
  // 하지만 컨트롤러만 보고도 어떤 역할을 하는 것인지 바로 파악할 수 있도록 하기 위해
  // @AuthorAccessOnly 와 같은 어노테이션을 적용하여 글쓴이 만이 접근할 수 있다라는 것을 직관적으로 나타낼 수 있다.
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod handlerMethod) {
      OwnerOnly annotation = handlerMethod.getMethodAnnotation(OwnerOnly.class);
      if (annotation != null) {
        Long postId = getPathVariablePostId(request);
        Long memberId = getUserIdFromAuthentication(request);
        Post post = findPost(postId);
        post.checkOwner(memberId);
      }
    }
    return false;
  }

  private Post findPost(Long postId) {
    return postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("not found post"));
  }

  private Long getPathVariablePostId(HttpServletRequest request) {
    Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    return Long.valueOf((String) pathVariables.get("id"));
  }

  private Long getUserIdFromAuthentication(HttpServletRequest request) {
    SecurityContext securityContext = (SecurityContext)request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
    UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
    return Long.parseLong(userDetails.getUsername());
  }
}
