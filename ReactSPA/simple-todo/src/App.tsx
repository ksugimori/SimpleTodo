import { useState } from 'react'
import TaskTable from './components/tasktable/TaskTable'
import Task, { completeTask } from './types/Task'
import TaskInputForm from './components/taskInputForm/TaskInputForm'
import TaskApiClient from './service/TaskApiClient'

const taskApiClient = new TaskApiClient('localhost', 8080);

const initialState: Task[] = await taskApiClient.findAll();

function App() {
  const [tasks, setTasks] = useState(initialState);

  return (
    <main>
      <h1>Simple TODO</h1>

      <section>
        <h2>新規登録</h2>

        <TaskInputForm onSubmit={async description => {
          const createdTask = await taskApiClient.create({
            description: description,
            isCompleted: false
          });

          const result = [...tasks, createdTask];
          setTasks(result);
        }} />
      </section>

      <section>
        <h2>一覧</h2>

        <TaskTable
          tasks={tasks}
          onComplete={async task => {
            const request = completeTask(task)

            try {
              const updatedTask = await taskApiClient.update(request);

              const result = tasks.map(e => (e.id === updatedTask.id ? updatedTask : e))
              setTasks(result);
            } catch {
              alert('更新に失敗しました');
            }
          }}
          onDelete={async id => {
            try {
              await taskApiClient.delete(id);

              const result = tasks.filter(task => task.id !== id);
              setTasks(result);
            } catch {
              alert('削除に失敗しました');
            }
          }}
        />
      </section>

    </main>
  )
}

export default App
