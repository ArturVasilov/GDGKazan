package ru.gdg.kazan.gdgkazan.screens.event;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Link;

/**
 * @author Artur Vasilov
 */
public class LinksAdapter extends RecyclerView.Adapter<LinkHolder> {

    private final List<Link> mLinks;

    private final OnLinkActionListener mActionListener;

    public LinksAdapter(@NonNull List<Link> links, @NonNull OnLinkActionListener actionListener) {
        mLinks = Collections.unmodifiableList(links);
        mActionListener = actionListener;
    }

    @NonNull
    @Override
    public LinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_link, parent, false);
        return new LinkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkHolder holder, int position) {
        holder.bind(mLinks.get(position), mActionListener);
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public interface OnLinkActionListener {
        void onLinkClick(@NonNull Link link);
    }

}
