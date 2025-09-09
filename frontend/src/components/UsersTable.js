import React, { useEffect, useState } from 'react';
import api from '../api';
export default function UsersTable() {
  const [rows, setRows] = useState([]);
  const [msg, setMsg] = useState('');
  useEffect(() => { api.get('/users').then(r => setRows(r.data)).catch(e => setMsg('Error ' + (e.response?.data?.message || e.message))); }, []);
  return (<div>
    <h2>Users</h2>
    {msg && <div>{msg}</div>}
    <table border="1" cellPadding="6">
      <thead><tr><th>ID</th><th>Email</th><th>Verified</th><th>Created</th></tr></thead>
      <tbody>{rows.map(r => (<tr key={r.id}><td>{r.id}</td><td>{r.email}</td><td>{r.verified ? 'Yes' : 'No'}</td><td>{r.createdAt}</td></tr>))}</tbody>
    </table>
  </div>);
}