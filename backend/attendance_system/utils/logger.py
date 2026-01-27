import logging

logger = logging.getLogger("speech_analysis")

if not logger.handlers:
    handler = logging.StreamHandler()
    formatter = logging.Formatter(
        "[%(levelname)s] %(asctime)s — %(message)s",
        "%H:%M:%S"
    )
    handler.setFormatter(formatter)
    logger.addHandler(handler)

logger.setLevel(logging.DEBUG)
