"use client";

import { useAuth } from "@/hooks/useAuth";

export default function Home() {
    const { isLoggedIn, logout } = useAuth(true); // ←ガードON

    if (!isLoggedIn) return null;

    return (
        <main>
            <div className="max-w-md mx-auto p-8">
                <h1 className="text-3xl font-bold mb-6">
                    人気ファンド比較
                </h1>
            </div>
        </main>
    );
}