"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

export function useAuth() {
    const [status, setStatus] = useState<"loading" | "auth" | "guest">("loading");
    const router = useRouter();

    const fetchMe = async () => {
        try {
            const res = await fetch("http://localhost:8080/api/auth/me", {
                credentials: "include",
            });

            if (!res.ok) {
                setStatus("guest");
                return false;
            }

            const data = await res.json();

            setStatus(data.loggedIn ? "auth" : "guest");
            return data.loggedIn;
        } catch (e) {
            setStatus("guest");
            return false;
        }
    };

    useEffect(() => {
        fetchMe();
    }, []);

    const login = async (username: string, password: string) => {
        const res = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify({ username, password }),
        });

        if (!res.ok) {
            return false;
        }

        await fetchMe();
        router.push("/");
        return true;
    };

    const logout = async () => {
        await fetch("http://localhost:8080/api/auth/logout", {
            method: "POST",
            credentials: "include",
        });

        setStatus("guest");
        router.push("/auth/login");
    };

    return {
        status,
        isLoggedIn: status === "auth",
        loading: status === "loading",
        login,
        logout,
    };
}
