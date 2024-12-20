type TaskId = number;

interface Task {
  readonly id: TaskId;
  description: string;
  readonly isCompleted: boolean;
}

function completeTask(task: Task): Task {
  return {
    id: task.id,
    description: task.description,
    isCompleted: true
  }
}

export type { TaskId, Task };
export { completeTask };
export default Task;