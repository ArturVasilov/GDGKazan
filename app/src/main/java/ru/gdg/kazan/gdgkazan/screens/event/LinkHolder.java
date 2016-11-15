package ru.gdg.kazan.gdgkazan.screens.event;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Link;
import ru.gdg.kazan.gdgkazan.utils.HtmlCompat;

/**
 * @author Artur Vasilov
 */
public class LinkHolder extends RecyclerView.ViewHolder {

    private static final String LINK_FORMAT = "<a href=\"%1$s\">%2$s</a>";

    private final TextView mLinkTextView;

    public LinkHolder(View itemView) {
        super(itemView);
        mLinkTextView = (TextView) itemView;
        mLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void bind(@NonNull Link link, @NonNull LinksAdapter.OnLinkActionListener actionListener) {
        CharSequence bullet = itemView.getContext().getString(R.string.event_links_bullet);
        SpannableString bulletSpannable = new SpannableString(bullet);
        bulletSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, 0);
        bulletSpannable.setSpan(new RelativeSizeSpan(1.5f), 0, 1, 0);
        bulletSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 1, 0);
        mLinkTextView.setText(bulletSpannable);

        String textWithLink = String.format(LINK_FORMAT, link.getUrl(), link.getTitle());
        mLinkTextView.append(createUrlText(textWithLink, link, actionListener));
    }

    /**
     * This method manually sets link for textview,
     * it's only done for Analytics purpose (to track link clicking)
     */
    @NonNull
    private CharSequence createUrlText(@NonNull String text, @NonNull Link link,
                                       @NonNull LinksAdapter.OnLinkActionListener actionListener) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        Spannable spannable = new SpannableString(HtmlCompat.fromHtml(text));
        URLSpan[] spans = spannable.getSpans(0, text.length(), URLSpan.class);
        for (URLSpan span : spans) {
            int start = spannable.getSpanStart(span);
            int end = spannable.getSpanEnd(span);
            spannable.removeSpan(span);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    actionListener.onLinkClick(link);
                }
            };
            spannable.setSpan(clickableSpan, start, end, 0);
        }
        return spannable;
    }

}
