package com.projects.mybooklist.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.projects.mybooklist.R;
import com.projects.mybooklist.entities.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    Context context;
    List<Book> books;
    OnItemClickListener onItemClickListener;
    private String highlightText;

    public BooksAdapter(Context context, List<Book> books) {
        this.books = books;
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

        setHighlightSearchedText(holder, book);

//      creating The Placeholder from the given rgb
        ColorDrawable imgPlaceHolder = new ColorDrawable(Color.rgb(
                book.getPlaceholderColor().getRed(),
                book.getPlaceholderColor().getGreen(),
                book.getPlaceholderColor().getBlue())
        );

//        loading image via glide -> The RGB background will be displayed until the image is fully loaded
        holder.book_RTB_rank.setRating(book.getRating());
        Glide.with(holder.itemView.getContext())
                .load(book.getUrl())
                .centerCrop()
                .placeholder(imgPlaceHolder)
                .into(holder.book_IMG_profile);

    }

    //generate highlighted text
    private Spannable generateSpannable(String text, int start, int end) {
        Spannable spannable = new SpannableString(text);
        ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, colorStateList, null);
        spannable.setSpan(textAppearanceSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private void setHighlightSearchedText(@NonNull BooksAdapter.BookViewHolder holder, Book book) {
        String title = book.getTitle();
        String body = book.getBody();

        if (highlightText != null && !highlightText.isEmpty()) {

            int titleStartPos = title.toLowerCase().indexOf(highlightText.toLowerCase());
            int titleEndPos = titleStartPos + highlightText.length();
            int bodyStartPos = body.toLowerCase().indexOf(highlightText.toLowerCase());
            int bodyEndPos = bodyStartPos + highlightText.length();

            if (titleStartPos != -1)
                holder.book_LBL_name.setText(generateSpannable(title, titleStartPos, titleEndPos));
            else
                holder.book_LBL_name.setText(title);

            if (bodyStartPos != -1)
                holder.book_LBL_author.setText(generateSpannable(body, bodyStartPos, bodyEndPos));
            else
                holder.book_LBL_author.setText(body);


        } else {
            holder.book_LBL_name.setText(title);
            holder.book_LBL_author.setText(body);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    //    update books that matches the search input
    public void filteredList(ArrayList<Book> filteredList, String text) {
        books = new ArrayList<>(filteredList);
        this.highlightText = text;
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

    //    creating an callback for implements Toast pop up when clicking on a book
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
}
