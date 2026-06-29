"use client";

import { useState } from "react";
import { useAuth } from "@/hooks/useAuth";

export default function LoginPage() {
    const { login } = useAuth(false);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    return (
        <main>
            <div className="max-w-md mx-auto mt-20 space-y-6">
                <h1 className="text-3xl font-bold mb-6 text-blue-500">
                    Login
                </h1>

                <div>
                    <label
                        className="block mb-1"
                    >
                        ユーザー名
                    </label>
                    <input
                        type="text"
                        placeholder="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="border p-2 w-full rounded"
                    />
                </div>

                <div>
                    <label
                        className="block mb-1"
                    >
                        パスワード
                    </label>
                    <input
                        type="password"
                        placeholder="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="border p-2 w-full rounded"
                    />
                </div>

                <div className="flex justify-center">
                    <button
                        onClick={() => login(username, password)}
                        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
                    >
                        Login
                    </button>
                </div>
            </div>
        </main>
    );
}