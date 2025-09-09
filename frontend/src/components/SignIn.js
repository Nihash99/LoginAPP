import React, { useState } from 'react';
import api from '../api';
export default function SignIn() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [msg, setMsg] = useState('');
  const login = async () => {
    try { await api.post('/auth/login', { email, password }); setMsg('Login success'); }
    catch (e) { setMsg('Error: ' + (e.response?.data?.message || e.message)); }
  };
  return (<div>
    <h2>Sign In</h2>
    <input placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} />
    <input placeholder="Password" type="password" value={password} onChange={e=>setPassword(e.target.value)} />
    <button onClick={login} disabled={!email || !password}>Login</button>
    <div style={{marginTop:10}}>{msg}</div>
  </div>);
}