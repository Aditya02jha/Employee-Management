import React from 'react';
import { Route, Routes, Link } from 'react-router-dom';
import EmployeeList from './components/EmployeeList';
import EmployeeForm from './components/EmployeeForm';
import EmployeeDetail from './components/EmployeeDetail';
import Homepage from './components/HomePage';

function App() {
  return (
    <div className="App">
      {/* <nav>
        <ul>
          <li><Link to="/">Employees</Link></li>
          <li><Link to="/create">Add Employee</Link></li>
        </ul>
      </nav> */}
      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/employees" element={<EmployeeList />} />
        <Route path="/create" element={<EmployeeForm />} />
        <Route path="/edit/:id" element={<EmployeeForm />} />
        <Route path="/employee/:id" element={<EmployeeDetail />} />
      </Routes>
    </div>
  );
}

export default App;
