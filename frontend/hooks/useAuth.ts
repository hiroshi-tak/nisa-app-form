"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

export function useAuth(requireAuth = false) {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const router = useRouter();

    const checkAuth = async () => {
        const res = await fetch("http://localhost:8080/api/user", {
            credentials: "include",
        });

        const ok = res.ok;
        setIsLoggedIn(ok);

        if (requireAuth && !ok) {
            router.push("/auth/login");
        }
    };

    useEffect(() => {
        checkAuth();
    }, []);

    const login = async (username: string, password: string) => {
        await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify({ username, password }),
        });

        await checkAuth();
        router.push("/");
    };

    const logout = async () => {
        await fetch("http://localhost:8080/api/auth/logout", {
            method: "POST",
            credentials: "include",
        });

        setIsLoggedIn(false);
        router.push("/auth/login");
    };

    return { isLoggedIn, login, logout };
}
