package org.salgar.spring.faces.webflow.context.portlet;

import java.lang.reflect.Method;

import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ReflectionUtils;

/**
 * Utilities when dealing with {@link PortletResponse}s.
 *
 * @since 2.4.0
 * @author Phillip Webb
 */
public class PortletResponseUtils {

	// Work around for PLUTO-603

	public static void setStatusCodeForPluto(PortletResponse response, int statusCode) {
		if (response.getClass().getName().startsWith("org.apache.pluto")) {
			Method servletResponseMethod = ReflectionUtils.findMethod(response.getClass(), "getServletResponse");
			if (servletResponseMethod != null) {
				try {
					ReflectionUtils.makeAccessible(servletResponseMethod);
					HttpServletResponse servletResponse = (HttpServletResponse) servletResponseMethod.invoke(response);
					servletResponse.setStatus(statusCode);
				} catch (Exception e) {
				}
			}
		}
	}
}
