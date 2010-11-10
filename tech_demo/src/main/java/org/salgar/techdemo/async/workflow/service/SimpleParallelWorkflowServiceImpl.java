package org.salgar.techdemo.async.workflow.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.salgar.techdemo.listener.AsyncListener;

/**
 * Basic ParallelWorkflowService Implementation that relies on reflection. backed by a ThreadPool of size 20.
 */
public class SimpleParallelWorkflowServiceImpl {
    private ExecutorService threadPoolBackedExecutorService = Executors.newScheduledThreadPool(20);
    private Object backendServiceInstance;

    /**
     * @see org.salgar.api.async.workflow.service.ParallelWorkflowService#asyncExecute(java.lang.Class,
     *      java.lang.String, java.lang.Object[], java.lang.Long, org.salgar.event.AsyncWorkflowListener)
     */
    public String asyncExecute(Class<?> interfaceNameOfServiceToBeInvoked, String methodName,
            Object[] methodParameterArray, Long timeout, AsyncListener listener) throws RuntimeException {
        String cid = "cid:" + UUID.randomUUID().toString();
        AsyncReflectionTask asyncTask = new AsyncReflectionTask(interfaceNameOfServiceToBeInvoked, methodName,
                methodParameterArray, timeout, listener, cid);
        threadPoolBackedExecutorService.submit(asyncTask);
        return cid;
    }

    /**
     * @see org.salgar.api.async.workflow.service.ParallelWorkflowService#execute(java.lang.Class,
     *      java.lang.String, java.lang.Object[], java.lang.Long)
     */
    public Serializable execute(Class<?> interfaceNameOfServiceToBeInvoked, String methodName,
            Object[] methodParameterArray, Long timeout) throws RuntimeException {
        Object result = null;

        try {
            Method method = ReflectionUtils.getMethod(interfaceNameOfServiceToBeInvoked, methodName,
                    methodParameterArray);
            result = method.invoke(backendServiceInstance != null ? backendServiceInstance
                    : interfaceNameOfServiceToBeInvoked.newInstance(), methodParameterArray);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return (Serializable) result;
    }

    public void setBackendServiceInstance(Object instance) {
        this.backendServiceInstance = instance;
    }

    public class AsyncReflectionTask implements Runnable {
        private Class<?> interfaceNameOfServiceToBeInvoked;
        private String methodName;
        private Object[] methodParameterArray;
        private Long timeout;
        private AsyncListener listener;
        private String correlationID;

        public AsyncReflectionTask(Class<?> interfaceNameOfServiceToBeInvoked, String methodName,
                Object[] methodParameterArray, Long timeout, AsyncListener listener, String cid) {
            this.interfaceNameOfServiceToBeInvoked = interfaceNameOfServiceToBeInvoked;
            this.methodName = methodName;
            this.methodParameterArray = methodParameterArray;
            this.timeout = timeout;
            this.listener = listener;
            this.correlationID = cid;
        }

        public void run() {

            Object result = null;

            try {
                Method method = ReflectionUtils.getMethod(interfaceNameOfServiceToBeInvoked, methodName,
                        methodParameterArray);

                result = method.invoke(backendServiceInstance != null ? backendServiceInstance
                        : interfaceNameOfServiceToBeInvoked.newInstance(), methodParameterArray);
            } catch (Exception e) {
                result = new RuntimeException(e);
            }

            listener.doResult(correlationID, result);
        }

    }

    public static class ReflectionUtils {
        public static Method getMethod(Class<?> interfaceClass, String methodName, Object[] parameterArray)
                throws SecurityException, NoSuchMethodException {
            Class<?>[] parameterType = null;
            parameterType = new Class<?>[parameterArray.length];
            for (int i = 0, n = parameterArray.length; i < n; i++) {
                if (parameterType == null) {
                    parameterType = new Class<?>[parameterArray.length];
                }
                parameterType[i] = parameterArray[i].getClass();
            }

            return interfaceClass.getMethod(methodName, parameterType);
        }
    }
}
