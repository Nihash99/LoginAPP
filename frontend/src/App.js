import React, { useState } from 'react';
import SignUp from './components/SignUp';
import SignIn from './components/SignIn';
import UsersTable from './components/UsersTable';
export default function App() {
  const [tab, setTab] = useState('signup');
  return (
    <div style={{maxWidth: 600, margin: '40px auto', fontFamily: 'system-ui, sans-serif'}}>
      <h1>Login / OTP Demo</h1>
      <div style={{display:'flex', gap: 12, marginBottom: 20}}>
        <button onClick={() => setTab('signup')}>Sign Up</button>
        <button onClick={() => setTab('signin')}>Sign In</button>
        <button onClick={() => setTab('users')}>Users</button>
      </div>
      {tab === 'signup' && <SignUp />}
      {tab === 'signin' && <SignIn />}
      {tab === 'users' && <UsersTable />}
    </div>
  );
}