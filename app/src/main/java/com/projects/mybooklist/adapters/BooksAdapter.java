package com.projects.mybooklist.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.projects.mybooklist.R;
import com.projects.mybooklist.entities.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    Context context;
    List<Book> books;
    List<Book> allBooks;
    OnItemClickListener onItemClickListener;

    public BooksAdapter(Context context, List<Book> books) {
        this.books = books;
        this.allBooks = new ArrayList<>(books);
        this.context = context;
    }


    @NonNull
    @Override
    public BooksAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(context).inflate(R.layout.book_list, parent, false);

        return new BookViewHolder(holder, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.BookViewHolder holder, int position) {
        Book book = books.get(position);
        Log.i("chatLog", "onBindViewHolder: " + book.toString());
        holder.book_LBL_name.setText(book.getTitle());
        holder.book_LBL_author.setText(book.getBody());
        ColorDrawable imgPlaceHolder = new ColorDrawable(Color.rgb(book.getPlaceholderColor().getRed(), book.getPlaceholderColor().getGreen(), book.getPlaceholderColor().getBlue()));

        holder.book_RTB_rank.setRating(book.getRating());
        Glide.with(holder.itemView.getContext())
                .load(book.getUrl())
                .centerCrop()
                .placeholder(imgPlaceHolder)
                .into(holder.book_IMG_profile);


    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void filteredList(ArrayList<Book> filteredList) {

        books = new ArrayList<>(filteredList);
        notifyDataSetChanged();
    }


    public static class BookViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView book_IMG_profile;
        TextView book_LBL_name;
        TextView book_LBL_author;
        RatingBar book_RTB_rank;

        public BookViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            book_LBL_name = itemView.findViewById(R.id.book_LBL_name);
            book_LBL_author = itemView.findViewById(R.id.book_LBL_author);
            book_RTB_rank = itemView.findViewById(R.id.book_RTB_rank);
            book_IMG_profile = itemView.findViewById(R.id.book_IMG_profile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
}
