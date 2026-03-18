'use client';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from "../component/navbar";

export default function Register() {
  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });
  const router = useRouter();
  const [status, setStatus] = useState({
    type: "",
    message: ""
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setStatus({ type: "loading", message: "Entrando..." });
    try {
        const res = await fetch("http://localhost:8080/user/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(formData)
        });
        if (!res.ok) {
          throw new Error("Erro ao entrar na conta");
        }
        const data = await res.json();
        setStatus("success", "Usuário logado com sucesso!");
        if (data.token) {
          localStorage.setItem("token", data.token);
        }
        setFormData({
          email: "",
          password: ""
        });
        setTimeout(() => {
            router.push("/");
            });
      } catch (error) {
        setStatus("error", "Erro ao entrar na conta");
      }
    };

  return (
    <>
      <Navbar />
      <div className="flex min-h-screen items-center justify-center bg-zinc-50 font-sans dark:bg-black px-4">
        <main className="w-full max-w-md bg-white dark:bg-zinc-900 p-8 rounded-2xl shadow-xl border border-zinc-200 dark:border-zinc-800">
          
          <div className="mb-8 text-center sm:text-left">
            <h1 className="text-3xl font-extrabold tracking-tight text-zinc-900 dark:text-zinc-50">
              Entrar Na Sua Conta
            </h1>
            <p className="text-zinc-500 dark:text-zinc-400 mt-2">
              Entre em sua conta por aqui
            </p>
          </div>
          {status.message && (
            <div className={`mb-4 p-3 rounded text-sm ${
              status.type === 'error' ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'
            }`}>
              {status.message}
            </div>
          )}

          <form onSubmit={handleSubmit} className="flex flex-col gap-5">

            <div className="flex flex-col gap-2">
              <label htmlFor="email" className="text-sm font-semibold text-zinc-700 dark:text-zinc-300">
                E-mail
              </label>
              <input 
                type="email" 
                id="email" 
                name="email" 
                value={formData.email}
                onChange={handleChange}
                placeholder="seu@email.com"
                className="w-full px-4 py-3 rounded-lg border border-zinc-300 dark:border-zinc-700 bg-zinc-50 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 focus:ring-2 focus:ring-blue-600 focus:border-transparent outline-none transition"
              />
            </div>
            <div className="flex flex-col gap-2">
              <label htmlFor="password" className="text-sm font-semibold text-zinc-700 dark:text-zinc-300">
                Senha
              </label>
              <input 
                type="password" 
                id="password" 
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="••••••••"
                className="w-full px-4 py-3 rounded-lg border border-zinc-300 dark:border-zinc-700 bg-zinc-50 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 focus:ring-2 focus:ring-blue-600 focus:border-transparent outline-none transition"
              />
            </div>
            
            <button 
              type="submit" 
              disabled={status.type === "loading"}
              className="mt-4 w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 rounded-lg shadow-lg shadow-blue-500/30 transition-all transform hover:-translate-y-0.5 active:scale-95"
            >
                {status.type === "loading" ? "Entrando..." : "Entrar"}
            </button>
          </form>

          <p className="mt-8 text-center text-sm text-zinc-500 dark:text-zinc-400">
            Não tem uma conta? <a href="/register" className="text-blue-600 hover:underline font-medium">Crie uma conta</a>
          </p>
        </main>
      </div>
    </>
  );
}