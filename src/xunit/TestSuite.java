package xunit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSuite implements Test{
    List<Test> tests = new ArrayList<>();

    public TestSuite(Class<? extends TestCase> testClass) {
        Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.getName().startsWith("test")) // 영상에서는 Spring Core 유틸 사용해서 어노테이션 찾음, AnnotationUtils.findAnnotation(m, Test.class) != null
                .forEach(m ->
                        {
                            try {
                                add(testClass.getConstructor(String.class).newInstance(m.getName()));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

    }

    public TestSuite() {

    }

    public void add(Test testMethod) {
        tests.add(testMethod);
    }

    public void run(TestResult result) {
        tests.forEach(t -> {
            t.run(result);
        });
    }
}
