import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class ExecutionTests {
    public static void main(String[] args) {
        Execute();
    }


    public static void Execute(){
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(MethodsTest.class))
                .build();
        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        /**
         *  Вывод результатов выполнения тестов
         */
        TestExecutionSummary summary = listener.getSummary();
        summary.printTo(new PrintWriter(System.out));
    }
}
