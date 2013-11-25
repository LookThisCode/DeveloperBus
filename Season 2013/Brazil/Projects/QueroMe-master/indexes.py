from dbindexer import autodiscover
autodiscover()

# AUTOLOAD_SITECONF module

# search for "search_indexes.py" in all installed apps
import search
search.autodiscover()