'use client';

import Link from 'next/link';
import { useRouter } from 'next/navigation';


export default function Header() {
    const router = useRouter();

    return (
        <header className="bg-blue-500 text-white sticky top-0 z-50">
            <div className="max-w-6xl mx-auto px-4 py-3 flex justify-between items-center">

                <Link
                    href="/"
                    className="text-xl font-bold"
                >
                    NISA初心者向けアプリ
                </Link>

                <nav className="flex gap-6 items-center">
                    <Link href="/">
                        トップページ
                    </Link>
                </nav>
            </div>
        </header>
    );
}