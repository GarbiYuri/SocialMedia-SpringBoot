'use client';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from "../component/navbar";

export default function EditProfile() {
  const [formData, setFormData] = useState({
    id: 0,
    username: "",
    email: "",
    photoPerfil: "",
    about: "",
    isActive: true,     
    enabled: true
  });
  
  const [selectedFile, setSelectedFile] = useState(null); 
  const [previewUrl, setPreviewUrl] = useState(null);
  const [status, setStatus] = useState({ type: "", message: "" });
  const router = useRouter();

  // 1. Carregar os dados atuais do usuário ao abrir a página
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      router.push("/login");
      return;
    }

    const fetchCurrentData = async () => {
      const res = await fetch("http://localhost:8080/user/me", {
        headers: { "Authorization": `Bearer ${token}` }
      });
      if (res.ok) {
        const data = await res.json();
        setFormData(data); 
      }
    };
    fetchCurrentData();
  }, [router]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
  e.preventDefault();
  const token = localStorage.getItem("token");
  setStatus({ type: "loading", message: "Processando..." });

  try {
    let updatedFormData = { ...formData };

    // --- Parte do Upload de Foto ---
    if (selectedFile) {
      const imageFormData = new FormData();
      imageFormData.append("file", selectedFile);

      const uploadRes = await fetch("http://localhost:8080/user/uploadPhoto", {
        method: "POST",
        headers: { "Authorization": `Bearer ${token}` },
        body: imageFormData
      });

      if (!uploadRes.ok) {
        // Se o upload falhar, tentamos pegar a mensagem do Java
        const errorData = await uploadRes.json().catch(() => ({}));
        throw new Error(errorData.message || "Erro no upload da imagem");
      }

      const uploadData = await uploadRes.json();
      updatedFormData.photoPerfil = uploadData.url; 
    }

    // --- Parte do Update de Perfil ---
    const editRes = await fetch(`http://localhost:8080/user/editUser?id=${formData.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify(updatedFormData)
    });

    if (!editRes.ok) {
      // AQUI É O SEGREDO: Pegar o corpo do erro 403/400
      const errorData = await editRes.json().catch(() => ({}));
      // Se o Java mandou uma mensagem, usamos ela, senão usamos a padrão
      throw new Error(errorData.message || "Erro ao atualizar perfil");
    }

    setStatus({ type: "success", message: "Perfil atualizado com sucesso! 🎉" });

  } catch (error) {
    console.log(error);
    // Agora o error.message será o texto que veio do Java!
    setStatus({ type: "error", message: error.message });
  }
};

  return (
    <>
      <Navbar />
      <div className="min-h-screen bg-zinc-50 dark:bg-black pt-24 px-4">
        <main className="max-w-2xl mx-auto bg-white dark:bg-zinc-900 p-8 rounded-2xl shadow-xl border border-zinc-200 dark:border-zinc-800">
          <h1 className="text-2xl font-bold text-zinc-900 dark:text-zinc-50 mb-6">Editar Perfil</h1>

          {status.message && (
            <div className={`mb-4 p-3 rounded text-sm ${
              status.type === 'error' ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'
            }`}>
              {status.message}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label className="block text-sm font-semibold dark:text-zinc-300">Username</label>
              <input 
                name="username" value={formData.username} onChange={handleChange}
                className="w-full mt-1 p-3 rounded-lg border dark:bg-zinc-800 dark:border-zinc-700 outline-none focus:ring-2 focus:ring-blue-600"
              />
            </div>
            <div>
              <label className="block text-sm font-semibold dark:text-zinc-300">Email</label>
              <input 
                name="email" value={formData.email} onChange={handleChange}
                className="w-full mt-1 p-3 rounded-lg border dark:bg-zinc-800 dark:border-zinc-700 outline-none focus:ring-2 focus:ring-blue-600"
              />
            </div>
            <div>
              <label className="block text-sm font-semibold dark:text-zinc-300">Sobre mim</label>
              <textarea 
                name="about" value={formData.about || ""} onChange={handleChange}
                placeholder="Conte um pouco sobre você..."
                className="w-full mt-1 p-3 rounded-lg border dark:bg-zinc-800 dark:border-zinc-700 outline-none focus:ring-2 focus:ring-blue-600 h-32"
              />
            </div>

            <div className="flex flex-col gap-4">
  <label className="block text-sm font-semibold dark:text-zinc-300">Foto de Perfil</label>
  
  <div className="flex items-center gap-4">
    {/* Preview da imagem: Prioriza o que o cara acabou de escolher, senão mostra a do banco */}
    <img 
  src={previewUrl || (formData.photoPerfil ? `http://localhost:8080${formData.photoPerfil}` : "/default-avatar.png")} 
  className="w-20 h-20 rounded-full object-cover border-2 border-blue-600"
  alt="Preview"
/>
    
    <input 
      type="file" 
      accept="image/*"
      onChange={(e) => {
  const file = e.target.files[0];
  if (file) {
    if (previewUrl) URL.revokeObjectURL(previewUrl);
    
    setSelectedFile(file);
    setPreviewUrl(URL.createObjectURL(file));
  }
}}
      className="block w-full text-sm text-zinc-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
    />
  </div>
</div>

            <button type="submit" className="w-full bg-blue-600 text-white font-bold py-3 rounded-lg hover:bg-blue-700 transition">
              Salvar Alterações
            </button>
          </form>
        </main>
      </div>
    </>
  );
}