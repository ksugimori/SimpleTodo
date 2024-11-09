import './App.css'
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
      <table id="task_table">
        <thead>
          <tr>
            <th>ID</th>
            <th>内容</th>
            <th></th>
          </tr>
        </thead>

        <tbody>
          {tasks.map(task => (<tr>
            <td>{task.id}</td>
            <td>{task.subject}</td>
            <td>{task.isCompleted
              ? '完了済み'
              : (<>
                <button type="button">完了</button>
                <button type='button'>削除</button>
              </>)
            }</td>
          </tr>))}
        </tbody>
      </table>

    </main>
  )
}

export default App
