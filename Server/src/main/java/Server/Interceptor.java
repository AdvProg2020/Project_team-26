package Server;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Interceptor implements HandlerInterceptor {
    private final long duration = 60000;
    private final int maxCount = 5;

    private List<String> ipList = new ArrayList<>();
    private List<Long> timeList = new ArrayList<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = getClientIp(request);
        long time = new Date().getTime();

        int count = 0;
        int i = timeList.size() - 1;
        while (i >= 0 && time - timeList.get(i) <= duration) {
            if (ipList.get(i).equals(ip)) {
                count++;
            }
            i--;
        }
        if (count >= maxCount) {
            return false;
        }

        ipList.add(ip);
        timeList.add(time);
        return true;
    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {
    }
}