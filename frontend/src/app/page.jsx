import Link from 'next/link';
import Navbar from "./component/navbar";

export default function Home() {
  return (
    <>
      <Navbar />
      
  
      <div className="min-h-screen bg-zinc-50 dark:bg-black font-sans pt-24 px-4 sm:px-6 lg:px-8">
        <main className="max-w-7xl mx-auto flex flex-col items-center justify-center text-center py-20 md:py-32"> 
          <div className="inline-flex items-center px-4 py-1.5 rounded-full text-sm font-semibold bg-blue-100 text-blue-700 dark:bg-blue-900/60 dark:text-blue-200 mb-8 border border-blue-200 dark:border-blue-800">
            🚀 Projeto Pessoal
          </div>
          <h1 className="text-5xl md:text-7xl font-extrabold tracking-tighter text-zinc-900 dark:text-white leading-tight">
            Compartilhando <span className="text-blue-600">Conhecimento</span><br />
            & Experiências.
          </h1>
          <p className="mt-8 max-w-2xl text-xl text-zinc-600 dark:text-zinc-400 leading-relaxed">
            Bem-vindo ao meu espaço digital. Aqui você encontra artigos sobre desenvolvimento web, 
            back-end em Java, segurança com JWT e a minha jornada estudando Análise e Desenvolvimento de Sistemas.
          </p>

          <div className="mt-12 flex flex-col sm:flex-row gap-4 sm:gap-6 w-full justify-center">
            <Link href="/posts" 
              className="inline-flex items-center justify-center px-8 py-3.5 rounded-xl bg-zinc-900 text-white dark:bg-white dark:text-black font-bold text-lg shadow-lg hover:bg-zinc-700 dark:hover:bg-zinc-200 transition-all transform hover:-translate-y-0.5 active:scale-95"
            >
              Ler os Artigos
              <svg className="w-5 h-5 ml-2.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7l5 5m0 0l-5 5m5-5H6" />
              </svg>
            </Link>
            
            <Link href="/about" 
              className="inline-flex items-center justify-center px-8 py-3.5 rounded-xl bg-white text-zinc-900 dark:bg-zinc-900 dark:text-white font-semibold text-lg border border-zinc-200 dark:border-zinc-800 hover:bg-zinc-100 dark:hover:bg-zinc-800 transition-all"
            >
              Sobre Mim
            </Link>
          </div>
          
        </main>

        <section className="max-w-7xl mx-auto py-16 border-t border-zinc-200 dark:border-zinc-800 mt-10">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 text-center sm:text-left">
            

            <div className="bg-white dark:bg-zinc-950 p-6 rounded-2xl border border-zinc-100 dark:border-zinc-800 shadow-sm">
              <div className="text-3xl mb-4">☕</div>
              <h3 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">Java Spring Boot</h3>
              <p className="text-zinc-600 dark:text-zinc-400 mt-2">APIs robustas, segurança com JWT e integração com PostgreSQL.</p>
            </div>

            <div className="bg-white dark:bg-zinc-950 p-6 rounded-2xl border border-zinc-100 dark:border-zinc-800 shadow-sm">
              <div className="text-3xl mb-4">⚛️</div>
              <h3 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">Next.js & React</h3>
              <p className="text-zinc-600 dark:text-zinc-400 mt-2">Frontend moderno, performático e com design responsivo.</p>
            </div>


            <div className="bg-white dark:bg-zinc-950 p-6 rounded-2xl border border-zinc-100 dark:border-zinc-800 shadow-sm">
              <div className="text-3xl mb-4">☁️</div>
              <h3 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">Deploy em VPS</h3>
              <p className="text-zinc-600 dark:text-zinc-400 mt-2">Gerenciamento de servidores Linux e deploy real da aplicação.</p>
            </div>

          </div>
        </section>

      </div>
    </>
  );
}