import './App.css'
import TaskTable from './components/tasktable/TaskTable'
import Task from './types/Task'

const tasks: Task[] = [
  { id: 1, subject: "掃除する", isCompleted: false },
  { id: 2, subject: "洗濯する", isCompleted: true }
]

function App() {
  return (
    <main>
      <h1>Simple TODO</h1>

      <h2>新規登録</h2>
      <form>
        <input type="text" /><button type="submit">登録</button>
      </form>

      <h2>一覧</h2>
      <TaskTable tasks={tasks} />

    </main>
  )
}

export default App
