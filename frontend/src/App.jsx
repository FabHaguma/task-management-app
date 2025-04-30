import { useEffect, useState } from 'react';
import './App.css';

function App() {
  const [tasks, setTasks] = useState([]);
  const [newTask, setNewTask] = useState('');

  useEffect(() => {
    fetch('http://localhost:8080/api/tasks')
      .then((response) => response.json())
      .then((data) => setTasks(data))
      .catch((error) => console.error('Error fetching tasks:', error));
  }, []);

  const handleAddTask = (e) => {
    e.preventDefault();

    const task = { description: newTask, completed: false };

    fetch('http://localhost:8080/api/tasks', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(task),
    })
      .then((response) => response.json())
      .then((createdTask) => {
        setTasks((prevTasks) => [...prevTasks, createdTask]);
        setNewTask(''); // Clear the input field
      })
      .catch((error) => console.error('Error adding task:', error));
  };

  const handleToggleComplete = (taskId) => {
    const task = tasks.find((task) => task.id === taskId);
    if (!task) return;

    fetch(`http://localhost:8080/api/tasks/${taskId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ ...task, completed: !task.completed }),
    })
      .then((response) => response.json())
      .then((updatedTask) => {
        setTasks((prevTasks) =>
          prevTasks.map((task) => (task.id === taskId ? updatedTask : task))
        );
      })
      .catch((error) => console.error('Error updating task:', error));
  }

  const handleDeleteTask = (taskId) => {
    fetch(`http://localhost:8080/api/tasks/${taskId}`, {
      method: 'DELETE',
    })
      .then(() => {
        setTasks((prevTasks) => prevTasks.filter((task) => task.id !== taskId));
      })
      .catch((error) => console.error('Error deleting task:', error));
  };

  return (
    <>
      <h1>Task List</h1>
      
      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            <div className="single-task" style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
              <label htmlFor={`task-${task.id}`} style={{ textDecoration: task.completed ? 'line-through' : 'none' }}>
                {task.description}
              </label>
              <span>{task.completed ? 'Completed' : 'Pending'}</span>
              <button onClick={() => handleToggleComplete(task.id)}>
                {task.completed ? 'Mark as Pending' : 'Mark as Completed'}
              </button>
              <button onClick={() => handleDeleteTask(task.id)}>Delete Task</button>
            </div>
          </li>
        ))}
      </ul>

      <h1>Add Task</h1>
      <form onSubmit={handleAddTask}>
        <input
          type="text"
          placeholder="Enter new task"
          value={newTask}
          onChange={(e) => setNewTask(e.target.value)}
          required
        />
        <button type="submit">Add Task</button>
      </form>
    </>
  );
}

export default App;
