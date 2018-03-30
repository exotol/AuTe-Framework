package ru.bsc.test.autotester.launcher;

/**
 * Created by smakarov
 * 30.03.2018 13:43
 */
public final class TestRunnerExitCodeContext {
    private static final TestRunnerExitCodeContext INSTANCE = new TestRunnerExitCodeContext();
    private int exitCode = 0;

    private TestRunnerExitCodeContext() {
    }

    public static TestRunnerExitCodeContext getInstance() {
        return INSTANCE;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }
}
