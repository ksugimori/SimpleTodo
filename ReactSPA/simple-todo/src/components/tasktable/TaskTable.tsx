import Task, { TaskId } from "../../types/Task"
import TaskTableRow from "./TaskTableRow";

type Props = {
  tasks: Task[];
  onComplete: (task: Task) => void;
  onDelete: (id: TaskId) => void;
}

function TaskTable({ tasks, onComplete, onDelete }: Props) {
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
        key={task.id}
        task={task}
        onComplete={onComplete}
        onDelete={onDelete}
      />)}
    </tbody>
  </table>
}

export default TaskTable;