"use client";

import Link from "next/link";

export default function Home() {

  return (
    <main>
      <div className="max-w-md mx-auto flex flex-col gap-4">
        <h1 className="text-3xl font-bold mb-6">
          トップページ
        </h1>

        <Link href="/funds" className="text-blue-600 underline">
          人気ファンド比較
        </Link>

        <Link href="/simulation" className="text-blue-600 underline">
          積立シミュレーション
        </Link>
      </div>
    </main>
  );
}
