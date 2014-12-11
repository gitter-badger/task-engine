package com.github.skyao.taskengine;

import com.github.skyao.taskengine.task.TaskPlan;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskPlanTest {
    @Test
    public void testBuilder() {
        TaskPlan plan = TaskPlan.newBuilder().priorityHigh().startNow().build();
        assertThat(plan.getRetry()).isNull();
        assertThat(plan.getSchedule()).isNull();
        System.out.println(plan);
    }

    @Test
    public void testBuilderWithRepeatPlan() {
        TaskPlan.Builder builder = TaskPlan.newBuilder().priorityHigh().startAfterSeconds(15);
        builder.enableSchedule().max(10).interval(2 * 1000).deadline(1);
        builder.enableRetry().max(3).interval(1 * 1000).deadline(1);
        TaskPlan plan = builder.build();
        assertThat(plan.getRetry()).isNotNull();
        assertThat(plan.getSchedule()).isNotNull();
        System.out.println(plan);
    }
}
