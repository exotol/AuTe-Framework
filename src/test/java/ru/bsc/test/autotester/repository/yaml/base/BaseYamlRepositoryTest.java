package ru.bsc.test.autotester.repository.yaml.base;

import org.junit.Test;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.component.impl.LimitTransliterationTranslator;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.repository.yaml.YamlProjectRepositoryImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaseYamlRepositoryTest {

    private BaseYamlRepository baseYamlRepository = new YamlProjectRepositoryImpl(new EnvironmentProperties(), new LimitTransliterationTranslator());

    @Test
    public void FindStepsForNewCodesEmptyTest() {
        assertTrue(invokeFindStepsForNewCodes(new Scenario(), new Scenario()).isEmpty());
        Scenario savedfilledScenario = getFilledScenario();
        Scenario filledScenario = getFilledScenario();
        assertTrue(invokeFindStepsForNewCodes(savedfilledScenario, filledScenario).isEmpty());
    }


    @Test
    public void FindStepsForNewCodesChangeCommentsTest() {
        Scenario savedFilledScenario = getFilledScenario();
        Scenario filledScenario = getFilledScenario();

        String stepComment0 = "Авторизация 1";
        String stepComment4 = "Негативный. Не задан тип в параметре type 1";
        String stepComment7 = "Негативный. отсутствует параметр value 1";

        filledScenario.getStepList().get(0).setStepComment(stepComment0);
        filledScenario.getStepList().get(4).setStepComment(stepComment4);
        filledScenario.getStepList().get(7).setStepComment(stepComment7);

        List<Step> stepsForNewCodes = invokeFindStepsForNewCodes(savedFilledScenario, filledScenario);

        List<String> stepComments = stepsForNewCodes.stream().map(s -> s.getStepComment()).collect(Collectors.toList());

        assertEquals(stepsForNewCodes.size(), 3);
        assertEquals(stepComments.indexOf(stepComment0), 0);
        assertEquals(stepComments.indexOf(stepComment4), 1);
        assertEquals(stepComments.indexOf(stepComment7), 2);

    }

    @Test
    public void FindStepsForNewCodesReplaceTest() {
        Scenario savedFilledScenario = getFilledScenario();
        Scenario filledScenario = getFilledScenario();

        // 1. проверка что удвлили шаги (есть в saved но нет в текущем)
        savedFilledScenario.getStepList().add(0, new Step());
        savedFilledScenario.getStepList().add(3, new Step());
        savedFilledScenario.getStepList().add(7, new Step());
        List<Step> stepsForNewCodes = invokeFindStepsForNewCodes(savedFilledScenario, filledScenario);
        assertTrue(stepsForNewCodes.isEmpty());

        // 2. проверка что добавили шаги (есть в текущемб но нет в saved)
        savedFilledScenario = getFilledScenario();
        filledScenario = getFilledScenario();

        filledScenario.getStepList().add(0, new Step());
        filledScenario.getStepList().add(3, new Step());
        filledScenario.getStepList().add(7, new Step());
        stepsForNewCodes = invokeFindStepsForNewCodes(savedFilledScenario, filledScenario);
        assertEquals(stepsForNewCodes.size(), 3);

        // 3. переместили шаги (3 и 5  поменяли местами)

        savedFilledScenario = getFilledScenario();
        filledScenario = getFilledScenario();
        Step tmp = filledScenario.getStepList().get(3);
        filledScenario.getStepList().set(3, filledScenario.getStepList().get(5));
        filledScenario.getStepList().set(5, tmp);
        stepsForNewCodes = invokeFindStepsForNewCodes(savedFilledScenario, filledScenario);
        assertEquals(stepsForNewCodes.size(), 2);
        assertEquals(stepsForNewCodes.get(0).getCode(), "4-negativnyi._ne_zadan_tip_v_par");
        assertEquals(stepsForNewCodes.get(1).getCode(), "6-negativnyi._ne_verno_zadan_tip");



        // 3. переместили шаги (1 и 3  поменяли местами потом  1 и 4 поменяли местами)

        savedFilledScenario = getFilledScenario();
        filledScenario = getFilledScenario();

        tmp = filledScenario.getStepList().get(1);
        filledScenario.getStepList().set(1, filledScenario.getStepList().get(3));
        filledScenario.getStepList().set(3, tmp);

        tmp = filledScenario.getStepList().get(1);
        filledScenario.getStepList().set(1, filledScenario.getStepList().get(4));
        filledScenario.getStepList().set(4, tmp);

        stepsForNewCodes = invokeFindStepsForNewCodes(savedFilledScenario, filledScenario);
        assertEquals(stepsForNewCodes.size(), 3);

        assertEquals(stepsForNewCodes.get(0).getCode(), "2-sozdanie_polzovatelskih_granto");
        assertEquals(stepsForNewCodes.get(1).getCode(), "4-negativnyi._ne_zadan_tip_v_par");
        assertEquals(stepsForNewCodes.get(2).getCode(), "5-negativnyi._ne_zadan_tip_v_par");
    }


    private Scenario getFilledScenario() {
        Scenario scenario = new Scenario();
        List<Step> steps = new ArrayList<>();

        steps.add(getStep("1-avtorizatziya", "Авторизация"));
        steps.add(getStep("2-sozdanie_polzovatelskih_granto", "Создание пользовательских грантов на вход для мобильных устройств-Пин-код"));
        steps.add(getStep("3-sozdanie_polzovatelskih_granto", "Создание пользовательских грантов на вход для мобильных устройств-Пин-код"));
        steps.add(getStep("4-negativnyi._ne_zadan_tip_v_par", "Негативный. Не задан тип в параметре type"));
        steps.add(getStep("5-negativnyi._ne_zadan_tip_v_par", "Негативный. Не задан тип в параметре value"));
        steps.add(getStep("6-negativnyi._ne_verno_zadan_tip", "Негативный. Не верно задан тип в параметре type"));
        steps.add(getStep("7-negativnyi._otsutstvuet_parame", "Негативный. отсутствует параметр  type"));
        steps.add(getStep("8-negativnyi._otsutstvuet_parame", "Негативный. отсутствует параметр value"));

        scenario.setStepList(steps);
        return scenario;
    }

    private Step getStep(String code, String comment){
        Step step = new Step();
        step.setCode(code);
        step.setStepComment(comment);
        return step;
    }


    private List<Step> invokeFindStepsForNewCodes(Scenario savedScenario, Scenario scenario) {
        Method method;
        try {
            method = BaseYamlRepository.class.getDeclaredMethod("findStepsForNewCodes", Scenario.class, Scenario.class);
            method.setAccessible(true);
            return (List<Step>) method.invoke(baseYamlRepository, savedScenario, scenario);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}