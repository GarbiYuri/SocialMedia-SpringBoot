'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

export default function Navbar() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const router = useRouter();
  const [name, setName] = useState("");
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [photoPerfil, setPhotoPerfil] = useState("");
  useEffect(() => { 
    const token = localStorage.getItem("token");
    if(token) {
      setIsLoggedIn(true);
      getInfo(token);
    }

    
  },[]);
  const getInfo = async (token) => {
    try {
    const res = await fetch("http://localhost:8080/user/me", {
        headers: {
          "Authorization": `Bearer ${token}`
        }
      });
      if (!res.ok) {
        throw new Error("Erro ao obter informações do usuário");
      } else if (res.status === 403 || res.status === 401) {
        handleLogout();
      }
    const data = await res.json();
    setName(data.username);
    setPhotoPerfil(data.photoPerfil);
    console.log(data.photoPerfil);
    console.log(data.username);

    } catch (error) {
      console.error("Erro ao obter informações do usuário:", error);
      handleLogout();
    }
  }
  
  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
    router.push("/");
  };
  return (
    <nav className="w-full bg-white dark:bg-gray-800 shadow-md">
      <div className="max-w-7xl mx-auto px-4 py-4 flex items-center justify-between">
        <div className="text-xl font-bold text-gray-800 dark:text-gray-200"><Link href="/">
            Blog App
          </Link></div>
        <div className="space-x-4">
          {isLoggedIn ? (
           <div className="relative inline-block text-left">
   
    <button
      onClick={() => setIsMenuOpen(!isMenuOpen)}
      className="flex items-center space-x-2 text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100 focus:outline-none transition-all"
    >
      <div className="w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center text-white text-xs font-bold">
        {photoPerfil ? (
    <img 
      src={`http://localhost:8080${photoPerfil}`}
      alt="Perfil"
      className="w-full h-full object-cover"
      onError={(e) => {
        // Se a foto não existir no servidor, ele remove a imagem e mostra a letra
        e.target.style.display = 'none';
        e.target.parentElement.innerHTML = name ? name.charAt(0).toUpperCase() : "U";
      }}
    />
  ) : (
    // Se não tiver foto no banco, mostra a letra (o que você já tinha)
    <span>{name ? name.charAt(0).toUpperCase() : "U"}</span>
  )}
      </div>
      <span className="font-medium">{name || "Usuário"}</span>
      
      <svg 
        className={`w-4 h-4 transition-transform ${isMenuOpen ? 'rotate-180' : ''}`} 
        fill="none" stroke="currentColor" viewBox="0 0 24 24"
      >
        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7" />
      </svg>
    </button>

    {isMenuOpen && (
      <>

        <div 
          className="fixed inset-0 z-10" 
          onClick={() => setIsMenuOpen(false)}
        ></div>

        <div className="absolute right-0 mt-2 w-48 bg-white dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 rounded-xl shadow-xl z-20 overflow-hidden animate-in fade-in zoom-in duration-200">
          <Link 
            href="/user" 
            className="block px-4 py-3 text-sm text-zinc-700 dark:text-zinc-300 hover:bg-zinc-100 dark:hover:bg-zinc-800 transition"
            onClick={() => setIsMenuOpen(false)}
          >
            Meu Perfil
          </Link>
          
          <button
            onClick={handleLogout}
            className="w-full text-left px-4 py-3 text-sm text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20 transition border-t border-zinc-100 dark:border-zinc-800 font-semibold"
          >
            Sair da conta
          </button>
        </div>
      </>
    )}
  </div>
          ) : (
            <>
              <Link href="/login" className="text-gray-600 dark:text-gray-400 hover:text-gray-800 dark:hover:text-gray-200">
                Login
              </Link>
              <Link href="/register" className="text-gray-600 dark:text-gray-400 hover:text-gray-800 dark:hover:text-gray-200">
                Register
              </Link>
            </>
          )}
        </div>
      </div>
      <div className="max-w-7xl mx-auto px-4 py-2 flex items-center justify-end space-x-4">
      </div>
    </nav>
  );
}