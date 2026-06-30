"use client";

import Link from "next/link";
import { useAuth } from '@/hooks/useAuth';


export default function Home() {
  const { status, logout } = useAuth();

  return (
    <main>

      <div className="max-w-md mx-auto flex flex-col gap-4">
        <div className="flex justify-end">
          {status === "auth" ? (
            <button
              onClick={logout}
              className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300"
            >
              Logout
            </button>
          ) : (
            <Link
              href="/auth/login"
              className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300"
            >
              Login
            </Link>
          )}
        </div>
      
        <div>
          <h1 className="text-3xl font-bold mb-6">
            トップページ
          </h1>
        </div>

        <div>
          <Link href="/funds" className="text-blue-600 underline">
            人気ファンド比較
          </Link>
        </div>

        <div>
          <Link href="/simulation" className="text-blue-600 underline">
            積立シミュレーション
          </Link>
        </div>

      </div>
    </main>
  );
}
