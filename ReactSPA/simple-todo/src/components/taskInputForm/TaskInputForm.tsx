import { FormEventHandler } from "react";

type Props = {
  onSubmit: (description: string) => void;
}

function TaskInputForm({ onSubmit }: Props) {
  const handler: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    const form: HTMLFormElement = event.currentTarget;

    const description = new FormData(form).get("description") as string
    onSubmit(description);

    form.reset();
  }

  return (<form onSubmit={handler}>
    <input type="text" name="description" required />
    <button type="submit">登録</button>
  </form>)
}

export default TaskInputForm;