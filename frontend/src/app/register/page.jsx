'use client';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from "../component/navbar";

export default function Register() {

    const router = useRouter();

  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: ""
  });
  
  const [status, setStatus] = useState({
    type: "",
    message: ""
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setStatus({ type: "loading", message: "Registrando..." });
    try {
        if (formData.password !== formData.confirmPassword) {
          setStatus({ type: "error", message: "As senhas não coincidem" });
          setFormData({ ...formData, confirmPassword: "" });
          return;
        }
        const res = await fetch("http://localhost:8080/user/register", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(formData)
        });
        
        if (!res.ok) {
          throw new Error("Erro ao registrar usuário");
        }
        const data = await res.json();
        setStatus("success", "Usuário registrado com sucesso!");
        setFormData({
          username: "",
          email: "",
          password: "",
          confirmPassword: ""
        });
        setTimeout(() => {
            router.push("/login");
            }, 1000);
      } catch (error) {
        setStatus("error", "Erro ao registrar usuário");
      }
    };

  return (
    <>
      <Navbar />
      <div className="flex min-h-screen items-center justify-center bg-zinc-50 font-sans dark:bg-black px-4">
        <main className="w-full max-w-md bg-white dark:bg-zinc-900 p-8 rounded-2xl shadow-xl border border-zinc-200 dark:border-zinc-800">
          
          <div className="mb-8 text-center sm:text-left">
            <h1 className="text-3xl font-extrabold tracking-tight text-zinc-900 dark:text-zinc-50">
              Criar conta
            </h1>
            <p className="text-zinc-500 dark:text-zinc-400 mt-2">
              Junte-se ao nosso blog e compartilhe suas ideias.
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
              <label htmlFor="username" className="text-sm font-semibold text-zinc-700 dark:text-zinc-300">
                Nome de usuário
              </label>
              <input 
                type="text" 
                id="username" 
                name="username" 
                value={formData.username}
                onChange={handleChange}
                placeholder="Ex: Joao123"
                className="w-full px-4 py-3 rounded-lg border border-zinc-300 dark:border-zinc-700 bg-zinc-50 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 focus:ring-2 focus:ring-blue-600 focus:border-transparent outline-none transition"
              />
            </div>

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
            <div className="flex flex-col gap-2">
              <label htmlFor="confirmPassword" className="text-sm font-semibold text-zinc-700 dark:text-zinc-300">
                Confirme A Senha
              </label>
              <input 
                type="password" 
                id="confirmPassword" 
                name="confirmPassword"
                value={formData.confirmPassword}
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
                {status.type === "loading" ? "Registrando..." : "Finalizar Cadastro"}
            </button>
          </form>

          <p className="mt-8 text-center text-sm text-zinc-500 dark:text-zinc-400">
            Já tem uma conta? <a href="/login" className="text-blue-600 hover:underline font-medium">Faça login</a>
          </p>
        </main>
      </div>
    </>
  );
}