package ru.bsc.test.autotester.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.bsc.test.autotester.service.VersionService;
import ru.bsc.test.autotester.service.impl.Version;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Golovkin
 */
public class VersionInterceptor extends HandlerInterceptorAdapter {

	private VersionService versionService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		Version version = versionService.getVersion();
		modelAndView.addObject("version", version);
		request.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Autowired
	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}
}
