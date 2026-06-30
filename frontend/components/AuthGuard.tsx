"use client";

import { useAuth } from "@/hooks/useAuth";
import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function AuthGuard({ children }: { children: React.ReactNode }) {
    const { status } = useAuth();
    const router = useRouter();

    useEffect(() => {
        if (status === "guest") {
            router.push("/auth/login");
        }
    }, [status]);

    if (status !== "auth") return null;

    return <>{children}</>;
}