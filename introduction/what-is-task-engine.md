**Task engine is a library which helps us to execute all kinds of asynchronize tasks easily.**

# Original Requirements

Before I begin to write down this library, I met many similar requirements in my daily development about how to execute asynchronize tasks:

1. At first, I coded by myself: for example, start java thread, take care every detail. 
2. Later I found that the JDK built-in Executor framework (since JDK 5.0) is very helpful to fulfill such requirements. 
3. But Executors are not enough for some common requirements, such as duplication detection, task retry, task priority, task deadline...... So I have to do many enhancement in different projects. 
4. Sometimes we will have the requirements about task schedule like starting this task 10 minutes later, or execute it 10 times for each hour. This is always called "timer".
5. We have to support task persistent so that we won't lost some unfinished task after restart or crash.
6. More complex, in a cluster environment, we will to execute the tasks (perhaps millions) in many servers. How to balance the work load? How to avoid task duplicated or loss? 

# Exists Solutions

To fulfill the requirements list above, we used many solutions: timer, message queue, and do many customized work. 

And the most important, we always choose different solution for different projects for all kinds reasons: the requirements, the server platform, the 3pp library we like......

It really waste many time and cost.

# Decision

So I would like to collect these requirements and try to provide a common solution (as I named "task engine"):

1. it helps to execute asynchronize tasks
2. it should provide several different implementations to fulfill different scene
3. it should wrapper some mature framework/library/service to avoid duplicated work
4. but it should provide common interface and definitions to separate the task and the executors, so that we can choose or change implementations freely


