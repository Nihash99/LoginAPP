import React, { useState } from 'react';
import api from '../api';
export default function SignUp() {
  const [email, setEmail] = useState('');
  const [step, setStep] = useState(1);
  const [otp, setOtp] = useState('');
  const [password, setPassword] = useState('');
  const [msg, setMsg] = useState('');
  const requestOtp = async () => {
    try { await api.post('/auth/request-otp', { email }); setMsg('OTP sent (or logged in backend console)'); setStep(2); }
    catch (e) { setMsg('Error: ' + (e.response?.data?.message || e.message)); }
  };
  const verifyOtp = async () => {
    try { await api.post('/auth/verify-otp', { email, otp, password }); setMsg('Verified! You can now sign in.'); setStep(3); }
    catch (e) { setMsg('Error: ' + (e.response?.data?.message || e.message)); }
  };
  return (<div>
    <h2>Sign Up</h2>
    {step === 1 && (<div>
      <input placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} />
      <button onClick={requestOtp} disabled={!email}>Request OTP</button>
    </div>)}
    {step === 2 && (<div>
      <input placeholder="OTP" value={otp} onChange={e=>setOtp(e.target.value)} />
      <input placeholder="Set Password" type="password" value={password} onChange={e=>setPassword(e.target.value)} />
      <button onClick={verifyOtp} disabled={!otp || !password}>Verify & Set Password</button>
    </div>)}
    {step === 3 && <div>Done. Go to Sign In tab.</div>}
    <div style={{marginTop:10}}>{msg}</div>
  </div>);
}