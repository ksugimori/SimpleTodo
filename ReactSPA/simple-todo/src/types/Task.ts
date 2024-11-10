type TaskId = number;

interface Task {
  readonly id: TaskId;
  subject: string;
  readonly isCompleted: boolean;
}

function completeTask(task: Task): Task {
  return {
    id: task.id,
    subject: task.subject,
    isCompleted: true
  }
}

export type { TaskId, Task };
export { completeTask };
export default Task;