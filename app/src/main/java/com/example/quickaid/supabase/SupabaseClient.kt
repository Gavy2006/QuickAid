package com.example.quickaid.supabase

import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientProvider {

    private const val SUPABASE_URL =
        "https://eyizqrotyzjegjhoybsy.supabase.co"

    private const val SUPABASE_ANON_KEY =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImV5aXpxcm90eXpqZWdqaG95YnN5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODMzMjczNTYsImV4cCI6MjA5ODkwMzM1Nn0.r77IMQB8CEv3a7eQe9qkfgwTQGdp4hZzHrTDItQc6ys"

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}