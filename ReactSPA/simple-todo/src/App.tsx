import { useState } from 'react'
import './App.css'
import TaskTable from './components/tasktable/TaskTable'
import Task from './types/Task'
import TaskInputForm from './components/taskInputForm/TaskInputForm'

const initialState: Task[] = [
  Task.create("掃除する").complete(),
  Task.create("洗濯する"),
]

function App() {
  const [tasks, setTasks] = useState(initialState);

  return (
    <main>
      <h1>Simple TODO</h1>

      <h2>新規登録</h2>
      <TaskInputForm onSubmit={subject => {
        const result = [...tasks, Task.create(subject)];
        setTasks(result);
      }} />

      <h2>一覧</h2>
      <TaskTable
        tasks={tasks}
        onComplete={id => {
          const result = tasks.map(task => (task.id === id ? task.complete() : task))
          setTasks(result);
        }}
        onDelete={id => {
          const result = tasks.filter(task => task.id !== id);
          setTasks(result);
        }}
      />

    </main>
  )
}

export default App
