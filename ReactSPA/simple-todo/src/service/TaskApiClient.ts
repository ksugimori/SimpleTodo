import Task, { TaskId } from "../types/Task";

type TaskCreateRequest = {
  description: string;
  isCompleted: boolean;
}

class TaskApiClient {
  private host: string;
  private resourcePath: string;

  constructor(host: string, port: number) {
    this.host = `http://${host}:${port}`
    this.resourcePath = '/api/tasks'
  }

  async findAll(): Promise<Task[]> {
    const url = new URL(`${this.host}${this.resourcePath}`);
    return fetch(url).then(response => response.json()) ?? [];
  }

  async create(task: TaskCreateRequest): Promise<Task> {
    const url = new URL(`${this.host}${this.resourcePath}`);
    return fetch(url, {
      method: 'POST',
      body: JSON.stringify({
        id: null, // 新規登録なので ID は null 固定
        description: task.description,
        isCompleted: task.isCompleted
      }),
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => response.json());
  }

  async update(task: Task): Promise<Task> {
    return fetch(`${this.host}${this.resourcePath}/${task.id}`, {
      method: "PUT",
      body: JSON.stringify(task),
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => response.json());
  }

  async delete(id: TaskId): Promise<Response> {
    return fetch(`${this.host}${this.resourcePath}/${id}`, {
      method: "DELETE",
    });
  }
}

export default TaskApiClient;