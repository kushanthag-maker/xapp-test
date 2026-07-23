package com.example.data

object MockMovies {
    val sampleMovies = listOf(
        Movie(
            id = "m1",
            title = "Cyber Horizon: 2099",
            synopsis = "In a futuristic neon metropolis, a rogue hacker discovers an AI neural network that controls human memories. She must team up with an ex-cybernetic detective to save humanity before their pasts are erased forever.",
            genre = listOf("Sci-Fi", "Action", "Cyberpunk"),
            rating = 4.9,
            voteCount = 14200,
            duration = "2h 18m",
            releaseYear = 2025,
            posterUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1200&q=80",
            trailerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            director = "Elena Vance",
            cast = listOf(
                CastMember("Maya Lin", "Kira (Hacker)", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&q=80"),
                CastMember("Marcus Vance", "Det. Cole", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200&q=80"),
                CastMember("David Zhang", "CEO Vance", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200&q=80")
            ),
            ageRating = "PG-13",
            quality = "4K Ultra HD",
            fileSizes = mapOf("720p HD" to "1.1 GB", "1080p Full HD" to "2.2 GB", "4K Ultra HD" to "6.5 GB")
        ),
        Movie(
            id = "m2",
            title = "Shadows of the Kingdom",
            synopsis = "An epic fantasy tale of a forgotten knight returning to reclaim the throne from ancient dark warlords and mythical dragons guarding the northern peaks.",
            genre = listOf("Action", "Fantasy", "Adventure"),
            rating = 4.8,
            voteCount = 28900,
            duration = "2h 45m",
            releaseYear = 2024,
            posterUrl = "https://images.unsplash.com/photo-1514539079130-25950c84af65?w=600&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1200&q=80",
            trailerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            director = "Arthur Pendelton",
            cast = listOf(
                CastMember("Johnathan Thorne", "Lord Gerald", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200&q=80"),
                CastMember("Elena Rostova", "Lady Eliana", "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=200&q=80")
            ),
            ageRating = "PG-13",
            quality = "4K Ultra HD",
            fileSizes = mapOf("720p HD" to "1.4 GB", "1080p Full HD" to "2.8 GB", "4K Ultra HD" to "7.2 GB")
        ),
        Movie(
            id = "m3",
            title = "Aloko Udapadi (Light Arose)",
            synopsis = "A legendary historical epic recounting the recording of the Tripitaka in ancient Sri Lanka during times of famine and invasion to preserve wisdom for future generations.",
            genre = listOf("History", "Drama", "Epic"),
            rating = 4.9,
            voteCount = 18400,
            duration = "2h 30m",
            releaseYear = 2023,
            posterUrl = "https://images.unsplash.com/photo-1542204165-65bf26472b9b?w=600&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=1200&q=80",
            trailerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            director = "Chathra Weeraman",
            cast = listOf(
                CastMember("Uddika Premarathna", "King Valagamba", "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=200&q=80"),
                CastMember("Dilhani Ekanayake", "Queen Somadevi", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&q=80")
            ),
            ageRating = "PG",
            quality = "1080p Full HD",
            isSinhalaCinema = true,
            fileSizes = mapOf("720p HD" to "950 MB", "1080p Full HD" to "1.9 GB", "4K Ultra HD" to "5.1 GB")
        ),
        Movie(
            id = "m4",
            title = "Gharasarapa (The Spell)",
            synopsis = "A gripping Sinhala romantic thriller exploring love, mystery, and ancient folklore set in the lush countryside of Sri Lanka.",
            genre = listOf("Romance", "Mystery", "Drama"),
            rating = 4.7,
            voteCount = 9200,
            duration = "2h 10m",
            releaseYear = 2022,
            posterUrl = "https://images.unsplash.com/photo-1485846234645-a62644f84728?w=600&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=1200&q=80",
            trailerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            director = "Jayantha Chandrasiri",
            cast = listOf(
                CastMember("Jackson Anthony", "Doctor", "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200&q=80"),
                CastMember("Kavindya Adikari", "Kalu", "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200&q=80")
            ),
            ageRating = "PG-13",
            quality = "1080p Full HD",
            isSinhalaCinema = true,
            fileSizes = mapOf("720p HD" to "880 MB", "1080p Full HD" to "1.7 GB", "4K Ultra HD" to "4.8 GB")
        ),
        Movie(
            id = "m5",
            title = "Interstellar Drift",
            synopsis = "When a deep space exploration vessel loses communications near a wormhole, a brave crew embarks on a high-stakes rescue mission across dimensions.",
            genre = listOf("Sci-Fi", "Adventure", "Thriller"),
            rating = 4.9,
            voteCount = 42100,
            duration = "2h 50m",
            releaseYear = 2024,
            posterUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=600&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=1200&q=80",
            trailerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            director = "Christopher Nolan style",
            cast = listOf(
                CastMember("Matthew Miller", "Capt. Cooper", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200&q=80"),
                CastMember("Jessica Chastain", "Dr. Brand", "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=200&q=80")
            ),
            ageRating = "PG-13",
            quality = "4K Ultra HD",
            fileSizes = mapOf("720p HD" to "1.5 GB", "1080p Full HD" to "3.1 GB", "4K Ultra HD" to "8.4 GB")
        ),
        Movie(
            id = "m6",
            title = "The Last Samurai of Kyoto",
            synopsis = "A master swordsman in feudal Japan must protect his village against rival clans while honoring an ancient code of honor and devotion.",
            genre = listOf("Action", "Drama", "History"),
            rating = 4.8,
            voteCount = 15300,
            duration = "2h 22m",
            releaseYear = 2025,
            posterUrl = "https://images.unsplash.com/photo-1528164344705-47542687990d?w=600&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=1200&q=80",
            trailerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            director = "Kenji Sato",
            cast = listOf(
                CastMember("Ken Watanabe", "Master Kenji", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200&q=80")
            ),
            ageRating = "R",
            quality = "4K Ultra HD",
            fileSizes = mapOf("720p HD" to "1.3 GB", "1080p Full HD" to "2.6 GB", "4K Ultra HD" to "7.0 GB")
        ),
        Movie(
            id = "m7",
            title = "Celestial Odyssey",
            synopsis = "An animated masterpiece following a young stargazer who discovers an enchanted telescope that reveals secret mythical creatures in the night sky.",
            genre = listOf("Animation", "Family", "Fantasy"),
            rating = 4.9,
            voteCount = 31200,
            duration = "1h 45m",
            releaseYear = 2024,
            posterUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=1200&q=80",
            trailerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            director = "Hayao Miyazaki Style",
            cast = listOf(
                CastMember("Aoi Yuu", "Voice of Luna", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&q=80")
            ),
            ageRating = "G",
            quality = "4K Ultra HD",
            fileSizes = mapOf("720p HD" to "850 MB", "1080p Full HD" to "1.6 GB", "4K Ultra HD" to "4.2 GB")
        ),
        Movie(
            id = "m8",
            title = "Ho Gaana Pokuna (Singing Pond)",
            synopsis = "An uplifting Sinhala family movie about a blind teacher who inspires young school children in a remote island village to build a choir.",
            genre = listOf("Family", "Drama", "Music"),
            rating = 4.9,
            voteCount = 12900,
            duration = "2h 05m",
            releaseYear = 2021,
            posterUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=600&q=80",
            backdropUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=1200&q=80",
            trailerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            director = "Indika Ferdinando",
            cast = listOf(
                CastMember("Anusuya Subasinghe", "Teacher Uma", "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=200&q=80"),
                CastMember("Lucien Bulathsinhala", "Principal", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200&q=80")
            ),
            ageRating = "G",
            quality = "1080p Full HD",
            isSinhalaCinema = true,
            fileSizes = mapOf("720p HD" to "900 MB", "1080p Full HD" to "1.8 GB", "4K Ultra HD" to "4.9 GB")
        )
    )

    val sampleReviews = listOf(
        MovieReview("r1", "m1", "Kamal Perera", "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100&q=80", 5f, "Mindblowing visual effects and cyberpunk theme! Must watch in 4K resolution.", "Yesterday"),
        MovieReview("r2", "m1", "Nipuni Fernando", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100&q=80", 4.5f, "Great storytelling and sound design. Downloaded for offline watching during my flight!", "3 days ago"),
        MovieReview("r3", "m3", "Kasun Silva", "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=100&q=80", 5f, "Truly proud of Sri Lankan cinema history. A masterpiece performance by Uddika!", "1 week ago")
    )
}
