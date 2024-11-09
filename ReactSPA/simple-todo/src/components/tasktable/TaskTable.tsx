import Task from "../../types/Task"
import TaskTableRow from "./TaskTableRow";

type Props = {
  tasks: Task[]
}

function TaskTable({ tasks }: Props) {
  return <table id="task_table">
    <thead>
      <tr>
        <th>ID</th>
        <th>内容</th>
        <th></th>
      </tr>
    </thead>

    <tbody>
      {tasks.map(task => <TaskTableRow
        task={task}
        onComplete={(t) => alert("完了しました: " + t.id)}
        onDelete={(t) => alert("削除しました: " + t.id)}
      />)}
    </tbody>
  </table>
}

export default TaskTable;