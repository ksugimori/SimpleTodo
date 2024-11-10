import { useState } from 'react'
import './App.css'
import TaskTable from './components/tasktable/TaskTable'
import Task, { completeTask } from './types/Task'
import TaskInputForm from './components/taskInputForm/TaskInputForm'

const initialState: Task[] = await fetch('http://localhost:8080/api/tasks')
  .then(response => response.json())
  ?? []

function App() {
  const [tasks, setTasks] = useState(initialState);

  return (
    <main>
      <h1>Simple TODO</h1>

      <h2>新規登録</h2>
      <TaskInputForm onSubmit={async subject => {
        const createdTask: Task = await fetch('http://localhost:8080/api/tasks', {
          method: "POST",
          body: JSON.stringify({
            id: null,
            subject: subject,
            isCompleted: false
          }),
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(response => response.json());

        const result = [...tasks, createdTask];
        setTasks(result);
      }} />

      <h2>一覧</h2>
      <TaskTable
        tasks={tasks}
        onComplete={async task => {
          const request = completeTask(task)
          await fetch(`http://localhost:8080/api/tasks/${task.id}`, {
            method: "PUT",
            body: JSON.stringify(request),
            headers: {
              'Content-Type': 'application/json'
            }
          }).catch(() => {
            alert('削除に失敗しました');
          });

          const result = tasks.map(e => (e.id === request.id ? request : e))
          setTasks(result);
        }}
        onDelete={async id => {
          await fetch(`http://localhost:8080/api/tasks/${id}`, {
            method: "DELETE",
          }).then(() => {
            const result = tasks.filter(task => task.id !== id);
            setTasks(result);
          }).catch(() => {
            alert('削除に失敗しました');
          });
        }}
      />

    </main>
  )
}

export default App
