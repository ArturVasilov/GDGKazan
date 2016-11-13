package ru.gdg.kazan.gdgkazan.screens.event;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Link;
import ru.gdg.kazan.gdgkazan.utils.HtmlCompat;

/**
 * @author Artur Vasilov
 */
public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.LinkHolder> {

    private final List<Link> mLinks;

    public LinksAdapter(@NonNull List<Link> links) {
        mLinks = Collections.unmodifiableList(links);
    }

    @NonNull
    @Override
    public LinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_link, parent, false);
        return new LinkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkHolder holder, int position) {
        holder.bind(mLinks.get(position));
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public static class LinkHolder extends RecyclerView.ViewHolder {

        private static final String LINK_FORMAT = "<a href=\"%1$s\">%2$s</a>";

        private final TextView mLinkTextView;

        public LinkHolder(View itemView) {
            super(itemView);
            mLinkTextView = (TextView) itemView;
            mLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        public void bind(@NonNull Link link) {
            CharSequence bullet = itemView.getContext().getString(R.string.event_links_bullet);
            SpannableString bulletSpannable = new SpannableString(bullet);
            bulletSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, 0);
            bulletSpannable.setSpan(new RelativeSizeSpan(1.5f), 0, 1, 0);
            bulletSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 1, 0);
            mLinkTextView.setText(bulletSpannable);

            String textWithLink = String.format(LINK_FORMAT, link.getUrl(), link.getTitle());
            mLinkTextView.append(HtmlCompat.fromHtml(textWithLink));
        }
    }

}
