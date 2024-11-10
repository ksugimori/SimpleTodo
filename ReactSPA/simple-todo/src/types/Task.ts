type TaskId = number;

class Task {
  private static sequence: TaskId = 1;

  readonly id: TaskId;
  readonly subject: string;
  readonly isCompleted: boolean;

  complete(): Task {
    return new Task(this.id, this.subject, true);
  }

  private constructor(id: TaskId, subject: string, isCompleted: boolean) {
    this.id = id;
    this.subject = subject;
    this.isCompleted = isCompleted;
  }

  public static create(subject: string): Task {
    return new Task(Task.sequence++, subject, false);
  }
}

export type { TaskId, Task };
export default Task;